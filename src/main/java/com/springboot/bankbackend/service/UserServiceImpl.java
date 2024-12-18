package com.springboot.bankbackend.service;


import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;
import com.springboot.bankbackend.repository.*;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;

import com.springboot.bankbackend.utils.Roles;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CustomUserDetailsService userDetailsService;
private final StepsRepository stepsRepository;
private final FriendChallengeRepository friendChallengeRepository;
private final EventRepository eventRepository;
private final DailyChallengeRepository dailyChallengeRepository;
//  private final OpenAIService openAIService;

  public UserServiceImpl(
          UserRepository userRepository,
          BCryptPasswordEncoder bCryptPasswordEncoder,
          CustomUserDetailsService userDetailsService, StepsRepository stepsRepository, FriendChallengeRepository friendChallengeRepository, EventRepository eventRepository, DailyChallengeRepository dailyChallengeRepository
  ) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
      this.stepsRepository = stepsRepository;
      this.friendChallengeRepository = friendChallengeRepository;
      this.eventRepository = eventRepository;
      this.dailyChallengeRepository = dailyChallengeRepository;
  }




// create new user
  @Override
  public UserResponse createUser(CreateUserRequest request) {

    UserEntity userEntity = new UserEntity();

    // Check if there is already a user with the same username
    if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
      throw new RuntimeException("Username already exists");
    }
// Create a new UserEntity
    userEntity.setUsername(request.getUsername());
    userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
userEntity.setTotalSteps(request.getTotalSteps());
userEntity.setCity(request.getCity());
userEntity.setAge(request.getAge());
userEntity.setPhoneNumber(request.getPhoneNumber());
userEntity.setHeight(request.getHeight());
userEntity.setWeight(request.getWeight());
userEntity.setRole(Roles.user);



    userEntity = userRepository.save(userEntity);

    UserResponse response =
        new UserResponse(userEntity.getId(), userEntity.getUsername(),userEntity.getAge(),
                userEntity.getCity(),userEntity.getTotalSteps(),
                userEntity.getWeight(),userEntity.getHeight(),userEntity.getRole().toString(),userEntity.getPoints()
        );
    return response;
  }

  @Override
  public UserResponse updateProfile(UpdateProfileRequest request) {
    // Retrieve the currently authenticated user
    UserEntity user = getAuthenticatedUser();

    // Update only non-null fields from the request
    if (request.getUsername() != null) {
      user.setUsername(request.getUsername());
    }
    if (request.getPassword() != null) {
      user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    }
    if (request.getAge() != null) {
      user.setAge(request.getAge());
    }
    if (request.getHeight() != null) {
      user.setHeight(request.getHeight());
    }
    if (request.getWeight() != null) {
      user.setWeight(request.getWeight());
    }
    if (request.getCity() != null) {
      user.setCity(request.getCity());
    }
    if (request.getPhoneNumber() != null) {
      user.setPhoneNumber(request.getPhoneNumber());
    }
    if (request.getTotalSteps() != null) {
      user.setTotalSteps(request.getTotalSteps());
    }
    if (request.getPoints() != null) {
      user.setPoints(request.getPoints());
    }


    // Save the updated user entity
    user = userRepository.save(user);

    // Create the response object
    UserResponse response = new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getAge(),
            user.getCity(),
            user.getTotalSteps(),
            user.getWeight(),
            user.getHeight(),
            user.getRole().toString(),
            user.getPoints()
    );

    return response;
  }


//  public UserResponse getProfile() {
//    UserEntity user = getAuthenticatedUser();
//
//    // Build the UserResponse with filtered transactions
//    UserResponse response = new UserResponse(user.getId(), user.getUsername(),user.getAge(),user.getCity(),
//            user.getTotalSteps(),user.getWeight(),user.getHeight(),user.getRole().toString());
//    return response;
//  }



  public UserResponse getUserProfile() {
    UserEntity user = getAuthenticatedUser();

    // Fetch total steps dynamically
    Long totalSteps = stepsRepository.findTotalStepsByUserId(user.getId());

    // Fetch challenge and event completion statuses
    List<StepsEntity> stepsEntities = stepsRepository.findByUserId(user.getId());
    List<ChallengeStatus> challenges = stepsEntities.stream().map(steps -> new ChallengeStatus(
            steps.getDailyChallenge() != null ? steps.getDailyChallenge().getId() : null,
            steps.getDailyChallenge() != null ? steps.getDailyChallenge().getDateTime() : null,
            steps.getEvent() != null ? steps.getEvent().getId() : null,
            steps.getEvent() != null ? steps.getEvent().getLocationName() : null,
            steps.getFriendChallenge() != null ? steps.getFriendChallenge().getId() : null,
            steps.getFriendChallenge() != null ? steps.getFriendChallenge().getName() : null,

            steps.getCompleted(),
            steps.getSteps()
    )).collect(Collectors.toList());

    return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getAge(),
            user.getCity(),
            totalSteps != null ? totalSteps : 0L,
            user.getWeight(),
            user.getHeight(),
            user.getRole().toString(),
            user.getPoints(),

            challenges // Include challenge statuses with names
    );
  }




  // get all users
  @Override
  public List<UserResponse> getAllUsers() {

    return userRepository.findAll()
            .stream()
            .map(user -> new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getAge(),
                    user.getCity(),
                    user.getTotalSteps(),
                    user.getWeight(),
                    user.getHeight(),
                    user.getRole().toString(),
                    user.getPoints()

            ))
            .collect(Collectors.toList());
  }


  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }


    private UserEntity getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Override
  public UserResponse getUserById(Long id) {
    return userRepository.findById(id)
            .map(user -> new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getAge(),
                    user.getCity(),
                    user.getTotalSteps(),
                    user.getWeight(),
                    user.getHeight(),
                    user.getRole().toString(),
                    user.getPoints()
            ))
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

  }





  @Override
  public void participateInFriendChallenge(Long userId, Long challengeId, List<Long> friendIds) {
    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    FriendChallengeEntity challenge = friendChallengeRepository.findById(challengeId)
            .orElseThrow(() -> new RuntimeException("Friend Challenge not found"));

    List<UserEntity> friends = userRepository.findAllById(friendIds);
    if (friends.isEmpty()) {
      throw new RuntimeException("No friends found");
    }

    // Add the user and their friends to the challenge
    friends.add(user);
    for (UserEntity participant : friends) {
      StepsEntity steps = new StepsEntity();
      steps.setUser(participant);
      steps.setFriendChallenge(challenge);
      steps.setSteps(0L); // Initially 0 steps
      stepsRepository.save(steps);
    }
  }


  @Override
  public void participateInEvent(Long userId,Long eventId){
    UserEntity user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
    EventEntity event = eventRepository.findById(eventId).orElseThrow(()-> new RuntimeException("Event not found"));

    // Create a new StepsEntity for the user`s participation
    StepsEntity steps = new StepsEntity();
    steps.setUser(user);
    steps.setEvent(event);
    steps.setSteps(0L); // Initially 0 steps

    // save event
    stepsRepository.save(steps);

  }
  @Override
  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
  }


  @Override
  public void participateInDailyChallenge(Long userId, Long dailyChallengeId) {
    // Fetch the user and daily challenge
    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    DailyChallengeEntity dailyChallenge = dailyChallengeRepository.findById(dailyChallengeId)
            .orElseThrow(() -> new RuntimeException("Daily Challenge not found"));

    // Create a new StepsEntity for participation
    StepsEntity steps = new StepsEntity();
    steps.setUser(user);
    steps.setDailyChallenge(dailyChallenge);
    steps.setSteps(0L); // Initial steps for participation

    // Save the steps entry
    stepsRepository.save(steps);
  }

  @Override
  public void addFriend(Long userId, Long friendId) {
    // Prevent adding oneself as a friend
    if (userId.equals(friendId)) {
      throw new IllegalArgumentException("You cannot add yourself as a friend.");
    }

    // Fetch user and friend
    UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    UserEntity friend = userRepository.findById(friendId)
            .orElseThrow(() -> new RuntimeException("Friend not found"));

    // Check if they are already friends
    if (user.getFriends().contains(friend)) {
      throw new RuntimeException("Friend already added");
    }

    // Add each user to the other's friend list for mutual friendship
    user.getFriends().add(friend);
    friend.getFriends().add(user);

    // Save changes
    userRepository.save(user);
    userRepository.save(friend);
  }


  @Override
  public List<UserResponse> getAllFriends(Long userId) {
    UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Map the user's friends to UserResponse
    return user.getFriends().stream()
            .map(friend -> new UserResponse(
                    friend.getId(),
                    friend.getUsername(),
                    friend.getAge(),
                    friend.getCity(),
                    friend.getTotalSteps(),
                    friend.getWeight(),
                    friend.getHeight(),
                    friend.getRole().toString(), // Assuming getRole() returns an Enum
                    friend.getPoints()

            ))
            .collect(Collectors.toList());
  }


  @Override
  public void updateStepsForDailyChallenge(Long userId, Long dailyChallengeId, Long steps) {
    List<StepsEntity> stepsEntities = stepsRepository.findByUserIdAndDailyChallengeId(userId, dailyChallengeId);

    if (stepsEntities.isEmpty()) {
      throw new RuntimeException("No participation found for this daily challenge");
    }

    // Log if duplicates exist
    if (stepsEntities.size() > 1) {
      System.out.println("Warning: Multiple records found for userId=" + userId + " and dailyChallengeId=" + dailyChallengeId);
    }

    // Update the first record (or handle duplicates as needed)
    StepsEntity stepsEntity = stepsEntities.get(0);
    stepsEntity.setSteps(stepsEntity.getSteps() + steps);

    // Check if target is met
    if (stepsEntity.getSteps() >= stepsEntity.getDailyChallenge().getFixedPoints()) {
      stepsEntity.setCompleted(true); // Mark as completed
    }

    stepsRepository.save(stepsEntity);
    // Update UserEntity totalSteps
    updateUserTotalSteps(userId);
  }



  @Override
  public void updateStepsForFriendChallenge(Long userId, Long friendChallengeId, Long steps) {
    List<StepsEntity> stepsEntities = stepsRepository.findByUserIdAndFriendChallengeId(userId, friendChallengeId);





    if (stepsEntities.isEmpty()) {
      throw new RuntimeException("No participation found for this friend challenge");
    }

    if (stepsEntities.size() > 1) {
      System.out.println("Warning: Multiple records found for userId=" + userId + " and friendChallengeId=" + friendChallengeId);
    }

    StepsEntity stepsEntity = stepsEntities.get(0);
    // check if the friend challenge is completed
    if (Boolean.TRUE.equals(stepsEntity.getCompleted())) {
      throw new RuntimeException("Friend challenge is already completed");
    }

    // Update steps
    stepsEntity.setSteps(stepsEntity.getSteps() + steps);

    // Check if target is met
    if (stepsEntity.getSteps() >= stepsEntity.getFriendChallenge().getStepGoal()) {
      stepsEntity.setCompleted(true); // Mark as completed
    }

    // Update UserEntity totalSteps
    updateUserTotalSteps(userId);
    // Save the steps
    stepsRepository.save(stepsEntity);
  }


  @Override
  public void updateStepsForEvent(Long userId, Long eventId, Long steps) {
    List<StepsEntity> stepsEntities = stepsRepository.findByUserIdAndEventId(userId, eventId);

    if (stepsEntities.isEmpty()) {
      throw new RuntimeException("No participation found for this event");
    }

    if (stepsEntities.size() > 1) {
      System.out.println("Warning: Multiple records found for userId=" + userId + " and eventId=" + eventId);
    }

    StepsEntity stepsEntity = stepsEntities.get(0);
    // check the event is completed
    if (Boolean.TRUE.equals(stepsEntity.getCompleted())) {
      throw new RuntimeException("Event is already completed");
    }
    // add the steps
    stepsEntity.setSteps(stepsEntity.getSteps() + steps);

    // Check if target is met
    if (stepsEntity.getSteps() >= stepsEntity.getEvent().getFixedPoints()) {
      stepsEntity.setCompleted(true); // Mark as completed
    }


    // Update UserEntity totalSteps
    updateUserTotalSteps(userId);
    // save the steps
    stepsRepository.save(stepsEntity);
  }


  //Helper function
  private void updateUserTotalSteps(Long userId) {
    Long totalSteps = stepsRepository.findTotalStepsByUserId(userId);

    UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setTotalSteps(totalSteps != null ? totalSteps : 0L); // Update totalSteps in UserEntity
    userRepository.save(user);
  }


  @Override
  public List<ParticipantProgress> getFriendChallengeProgress(Long friendChallengeId) {
    List<StepsEntity> stepsEntities = stepsRepository.findByFriendChallengeId(friendChallengeId);

    if (stepsEntities.isEmpty()) {
      throw new RuntimeException("No participants found for this friend challenge");
    }

    return stepsEntities.stream()
            .map(steps -> new ParticipantProgress(
                    steps.getUser().getId(),
                    steps.getUser().getUsername(),
                    steps.getSteps(),
                    steps.getFriendChallenge().getStepGoal(),
                    steps.getCompleted()
            ))
            .collect(Collectors.toList());
  }


}
