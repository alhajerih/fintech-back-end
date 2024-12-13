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
                userEntity.getWeight(),userEntity.getHeight(),userEntity.getRole().toString()
        );
    return response;
  }

  @Override
  public UserResponse updateProfile(UpdateProfileRequest request) {
    UserEntity user = getAuthenticatedUser();
    user.setUsername(request.getUsername());
    user.setId(user.getId());
    user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    user.setAge(request.getAge());
    user.setHeight(request.getHeight());
    user.setWeight(request.getWeight());
    user.setCity(request.getCity());
    user.setPhoneNumber(request.getPhoneNumber());
    user = userRepository.save(user);
    UserResponse response = new UserResponse(user.getId(), user.getUsername(),user.getAge(),user.getCity(),
            user.getTotalSteps(),user.getWeight(),user.getHeight(),user.getRole().toString());

    return response;
  }

  public UserResponse getProfile() {
    UserEntity user = getAuthenticatedUser();

    // Build the UserResponse with filtered transactions
    UserResponse response = new UserResponse(user.getId(), user.getUsername(),user.getAge(),user.getCity(),
            user.getTotalSteps(),user.getWeight(),user.getHeight(),user.getRole().toString());
    return response;
  }



  public UserResponse getUserProfile() {
    // Fetch the authenticated user
    UserEntity user = getAuthenticatedUser();

    // Calculate total steps dynamically
    Long totalSteps = stepsRepository.findTotalStepsByUserId(user.getId());

    // Return a UserResponseBO that includes the total steps
    return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getAge(),
            user.getCity(),
            totalSteps != null ? totalSteps : 0L, // Handle null case
            user.getWeight(),
            user.getHeight(),
            user.getRole().toString()
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
                    user.getRole().toString()
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
                    user.getRole().toString()
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
    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    UserEntity friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));

    if (user.getFriends().contains(friend)) {
      throw new RuntimeException("Friend already added");
    }

    // Add the friend to the user's friend list
    user.getFriends().add(friend);
    friend.getFriends().add(user);

    //save both user and his friends
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
                    friend.getRole().toString() // Assuming getRole() returns an Enum
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
    stepsRepository.save(stepsEntity);
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
    stepsEntity.setSteps(stepsEntity.getSteps() + steps);
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
    stepsEntity.setSteps(stepsEntity.getSteps() + steps);
    stepsRepository.save(stepsEntity);
  }


}
