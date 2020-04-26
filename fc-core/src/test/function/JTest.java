package function;

import com.easyarch.dao.mapper.UserMapper;
import com.easyarch.factory.UserFactory;
import com.easyarch.model.Message;
import com.easyarch.model.UserInfo;
import com.easyarch.model.code.CODE;
import com.easyarch.utils.config.NettyConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {NettyConfig.class})
public class JTest {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private UserMapper userDao;

    @Test
    public void testForLogin(){
        Message message = new Message();
        UserInfo user = new UserInfo();
        //这里替换测试用例
        user.setUserId("iiii");
        user.setUserPwd("123456");
        //这里替换是登录还是注册操作
        message.setMsgCode(CODE.LOGIN);
        message.setObj(user);
        //计算时间
        long start = System.currentTimeMillis();
        System.out.println(userFactory.handle(message));
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Test
    public void testForRegister(){
        Message message = new Message();
        UserInfo user = new UserInfo();
        //这里替换测试用例
        user.setUserId("iiii");
        user.setUserPwd("123456");
        //这里替换是登录还是注册操作
        message.setMsgCode(CODE.REGIST);
        message.setObj(user);
        //计算时间
        long start = System.currentTimeMillis();
        System.out.println(userFactory.handle(message));
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
