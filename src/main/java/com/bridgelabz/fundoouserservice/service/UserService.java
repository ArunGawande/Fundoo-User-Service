package com.bridgelabz.fundoouserservice.service;

import com.bridgelabz.fundoouserservice.dto.UserServiceDTO;
import com.bridgelabz.fundoouserservice.exception.FundooUserNotFoundException;
import com.bridgelabz.fundoouserservice.model.UserServiceModel;
import com.bridgelabz.fundoouserservice.repository.UserRepository;
import com.bridgelabz.fundoouserservice.util.Response;
import com.bridgelabz.fundoouserservice.util.ResponseUtil;
import com.bridgelabz.fundoouserservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;

    @Override
    public Response addUser(UserServiceDTO userServiceDTO) {
        UserServiceModel userServiceModel = new UserServiceModel(userServiceDTO);
        userServiceModel.setCreatedAt(LocalDateTime.now());
        userRepository.save(userServiceModel);
        String body = "Fundoo user is added sucessfully with userId " + userServiceModel.getId();
        String subject = "Fundoo user is added sucessfully";
        mailService.send(userServiceModel.getEmailId(), body, subject);
        return new Response(200, "sucessfully", userServiceModel);
    }

    @Override
    public ResponseUtil login(String emailId, String password) {
        Optional<UserServiceModel> isEmailPresent = userRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            if (isEmailPresent.get().getPassword().equals(password)) {
                String token = tokenUtil.createToken(isEmailPresent.get().getId());
                return new ResponseUtil(200, "Login sucessfully", token);
            }
            throw new FundooUserNotFoundException(400, "Password is wrong");
        }
        throw new FundooUserNotFoundException(400, "No user is present with this email id");
    }

    @Override
    public Response updateUser(UserServiceDTO userServiceDTO, Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserServiceModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setName(userServiceDTO.getName());
                isUserPresent.get().setEmailId(userServiceDTO.getEmailId());
                isUserPresent.get().setPassword(userServiceDTO.getPassword());
                isUserPresent.get().setDOB(userServiceDTO.getDOB());
                isUserPresent.get().setPhoneNumber(userServiceDTO.getPhoneNumber());
                isUserPresent.get().setProfilePic(userServiceDTO.getProfilePic());
                userRepository.save(isUserPresent.get());
                String body = "Fundoo user is added sucessfully with userId" + isUserPresent.get().getId();
                String subject = "Fundoo user is added sucessfully";
                mailService.send(isUserPresent.get().getEmailId(), body, subject);
                return new Response(200, "Sucessfully", isUserPresent.get());
            } else {
                throw new FundooUserNotFoundException(400, "User not present");
            }
        }
        throw new FundooUserNotFoundException(400, "Token is wrong");
    }

    @Override
    public List<UserServiceModel> getUsers(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            List<UserServiceModel> isUserPresent = userRepository.findAll();
            if (isUserPresent.size() > 0) {
                return isUserPresent;
            } else {
                throw new FundooUserNotFoundException(400, "User not present");
            }
        }
        throw new FundooUserNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response deleteUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserServiceModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                userRepository.delete(isUserPresent.get());
                return new Response(200, "Sucessfully", isUserPresent.get());
            } else {
                throw new FundooUserNotFoundException(400, "User not present");
            }
        }
        throw new FundooUserNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response getUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserServiceModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                return new Response(200, "Sucessfully", isUserPresent.get());
            } else {
                throw new FundooUserNotFoundException(400, "User not present");
            }
        }
        throw new FundooUserNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response updatePassword(String token, String password) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            isId.get().setPassword(password);
            userRepository.save(isId.get());
            return new Response(200, "Sucessfully", isId.get());
        } else {
            throw new FundooUserNotFoundException(400, "Token is wrong");
        }
    }

    @Override
    public Response resetPassword(String emailId) {
        Optional<UserServiceModel> isEmailPresent = userRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = System.getenv("url");
            String body = "Reset the password using this link \n " + url +
                    "\n This token is use to reset the password \n" + token;
            String subject = "Reset password sucessfully";
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
            return new Response(200, "Sucessfull", isEmailPresent.get());
        } else {
            throw new FundooUserNotFoundException(400, "Wrong email id");
        }

    }

    @Override
    public Boolean validate(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Response restoreUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserServiceModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setActive(true);
                isUserPresent.get().setDeleted(false);
                userRepository.save(isUserPresent.get());
                return new Response(200, "Sucessfully", isUserPresent.get());
            } else {
                throw new FundooUserNotFoundException(400, "User not found with this id");
            }
        } else {
            throw new FundooUserNotFoundException(400, "Token is wrong");
        }
    }

    @Override
    public Response deleteUsers(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserServiceModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setActive(false);
                isUserPresent.get().setDeleted(true);
                userRepository.save(isUserPresent.get());
                return new Response(200, "Sucessfully", isUserPresent.get());
            } else {
                throw new FundooUserNotFoundException(400, "User not found with this id");
            }
        } else {
            throw new FundooUserNotFoundException(400, "Token is wrong");
        }
    }

    @Override
    public Response deletePermanent(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserServiceModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                userRepository.delete(isUserPresent.get());
                return new Response(200, "Sucessfully", isUserPresent.get());
            } else {
                throw new FundooUserNotFoundException(400, "User not found with this id");
            }
        } else {
            throw new FundooUserNotFoundException(400, "Token is wrong");
        }

    }

}

