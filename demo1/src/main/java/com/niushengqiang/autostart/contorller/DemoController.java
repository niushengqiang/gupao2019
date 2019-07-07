package com.niushengqiang.autostart.contorller;

import com.niushengqiang.autostart.ObjFormate;
import com.niushengqiang.autostart.properties.PropertiesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private ObjFormate objFormate;

    @Autowired
    private PropertiesInfo propertiesInfo;


    @GetMapping("/doformat")
    public String doFormat(){
        User user = new User();
        user.setName("yurang");
        user.setAge(18);

        StringBuffer buffer = new StringBuffer();
        buffer.append("类的格式化");
        buffer.append("objFormate.format(user)");
        buffer.append("<br/>");

        buffer.append("进行配置文件的读取");
        buffer.append(propertiesInfo.toString());
        return buffer.toString();
    }


    private class User {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User的toString 结果==》" +
                    "【name='" + name + '\''+", age='" + age + '\'' + '】';
        }
    }

}
