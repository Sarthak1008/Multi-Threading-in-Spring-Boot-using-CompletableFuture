package com.example.MultiThreading.using.CompletableFuture.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.MultiThreading.using.CompletableFuture.entities.User;
import com.example.MultiThreading.using.CompletableFuture.repositorities.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    Object target;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    /*
     * Async nnotation that marks a method as a candidate for asynchronous
     * execution.
     * 
     * Can also be used at the type level, in which case all the type's methods are
     * considered as asynchronous. Note, however, that @Async is not supported on
     * methods declared within a @Configuration class.
     * 
     * Completable Future In terms of target method signatures, any parameter types
     * are supported.
     * However, the return type is constrained to either void or
     * java.util.concurrent.Future. In the latter case, you may declare the more
     * specific org.springframework.util.concurrent.ListenableFuture or
     * java.util.concurrent.CompletableFuture types which allow for richer
     * interaction with the asynchronous task and for immediate composition with
     * further processing steps.
     * 
     * A Future handle returned from the proxy will be an actual asynchronous Future
     * that can be used to track the result of the asynchronous method execution.
     * However, since the target method needs to implement the same signature, it
     * will have to return a temporary Future handle that just passes a value
     * through
     */

    @Async
    public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<User> users = parseCSVFile(file);
        logger.info("saving list of users of size {}", users.size(), "" + Thread.currentThread().getName());
        users = repository.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<User>> findAllUsers() {
        logger.info("get list of user by " + Thread.currentThread().getName());
        List<User> users = repository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        try {
            /*
             * BufferedReader reads text from a character-input stream, buffering characters
             * so as to provide for the efficient reading of characters, arrays, and lines.
             * 
             * The buffer size may be specified, or the default size may be used. The
             * default is large enough for most purposes.
             * 
             * In general, each read request made of a Reader causes a corresponding read
             * request to be made of the underlying character or byte stream. It is
             * therefore advisable to wrap a BufferedReader around any Reader whose read()
             * operations may be costly, such as FileReaders and InputStreamReader
             */
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final User user = new User();
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    users.add(user);
                }
                return users;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}
