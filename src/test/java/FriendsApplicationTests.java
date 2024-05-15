import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 启动类测试
 *
 */
//@SpringBootTest
class FriendsApplicationTests {


    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String newPassword = DigestUtils.md5DigestAsHex(("simple" + "12345").getBytes());
        System.out.println(newPassword);
    }


    @Test
    void contextLoads() {

    }

}