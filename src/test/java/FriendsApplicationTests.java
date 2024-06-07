import com.simple.friends.model.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ResolvableType;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * 启动类测试
 *
 */
@Slf4j
//@SpringBootTest
class FriendsApplicationTests {


    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String newPassword = DigestUtils.md5DigestAsHex(("simple" + "123456").getBytes());
        System.out.println(newPassword);
    }


    @Test
    void contextLoads() {
//        Object users = null;
//        Users users1 = (Users) users;

        try {
            byte[] decodedCookieBytes = Base64.getDecoder().decode("NGFhZTlhYzYtZTE3ZC00MmFlLTg4YzYtZTE4MjAwMjE1MDQ3");
            log.info("base64: {}", new String(decodedCookieBytes));
        }
        catch (Exception ex) {
            log.debug("Unable to Base64 decode value: " + "base64Value");
        }
    }

    interface If<T extends Number> {

    }

    static class B<T> {}

    static class A extends B<String> implements If<Integer>{

    }

    @Test
    public void tt() {
        A a = new A();

//        Type genericSuperclass = A.class.getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType) {
//            ParameterizedType pt = (ParameterizedType) genericSuperclass;
//            System.out.println(Arrays.toString(pt.getActualTypeArguments()));
//        }

        // 自己只管自己的参数。获取extends或者implments的通过获取父类泛型或者接口泛型
        TypeVariable<Class<If>>[] typeParameters = If.class.getTypeParameters();
        for (TypeVariable<Class<If>> typeParameter : typeParameters) {
            System.out.println(Arrays.toString(typeParameter.getBounds()));
            System.out.println(typeParameter.getGenericDeclaration());
        }

//        ResolvableType




        Type[] genericInterfaces = a.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
//            System.out.println(genericInterface.getTypeName());
//            System.out.println(genericInterface instanceof TypeVariable);
//            System.out.println(genericInterface instanceof ParameterizedType);
//            System.out.println(genericInterface instanceof WildcardType);
//            System.out.println(genericInterface instanceof GenericArrayType);
//            System.out.println(genericInterface instanceof Class);
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericInterface;
                System.out.println(Arrays.toString(pt.getActualTypeArguments()));


            }


        }
        System.out.println(Arrays.asList(A.class.getInterfaces()));

//        Class<?> [] cls = A.class.getInterfaces();
//        for (Class<?> c : cls) {
//            Type[] genericInterfaces1 = c.getGenericInterfaces();
//
//            for (Type type : genericInterfaces1) {
//                if (type instanceof TypeVariable) {
//                   TypeVariable ty = (TypeVariable)type;
//
//                } else if (type instanceof ParameterizedType) {
//                    System.out.println("Par");
//                    ParameterizedType pt = (ParameterizedType) type;
//                    System.out.println(Arrays.toString(pt.getActualTypeArguments()));
//                    System.out.println(pt.getOwnerType());
//                    System.out.println(pt.getRawType());
//                }
//            }
//        }
    }

}