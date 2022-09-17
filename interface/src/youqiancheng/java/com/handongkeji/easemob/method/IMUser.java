package com.handongkeji.easemob.method;

import com.handongkeji.easemob.api.impl.EasemobIMUsers;
import io.swagger.client.model.NewPassword;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by easemob on 2017/3/21.
 */
@Slf4j
public class IMUser {

    private static EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
    //注册单个用户，批量注册
    @Test
    public static void createUser(List<User> userList) {
        RegisterUsers users = new RegisterUsers();
        for (User u:userList) {
            users.add(u);
        }
       // User user = new User().username("aaaa12345678911").password("123456");
//        User user1 = new User().username("aaa123456" + new Random().nextInt(500)).password("123456");
       // users.add(user);
//        users.add(user1);
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        log.info(result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void getUserByName() {
        String userName = "stringa";
        Object result = easemobIMUsers.getIMUserByUserName(userName);
        log.info(result.toString());
    }

    @Test
    public void gerUsers() {
        Object result = easemobIMUsers.getIMUsersBatch(5L, null);
        log.info(result.toString());
    }

    @Test
    public void changePassword() {
        String userName = "stringa";
        NewPassword psd = new NewPassword().newpassword("123");
        Object result = easemobIMUsers.modifyIMUserPasswordWithAdminToken(userName, psd);
        log.info(result.toString());
    }

    @Test
    public void getFriend() {
        String userName = "stringa";
        Object result = easemobIMUsers.getFriends(userName);
        log.info(result.toString());
    }
}
