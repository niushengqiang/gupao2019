## 问题

#### 1、resultType（属性）和resultMap（标签引用）的区别？

* resultType
  * 写的是映射的类前路径名.
  * 默认实体类和数据库字段相符才能赋值
* resultMap
  * 是自己在mapper文件中声明的.里面可以自定义映射.实体类不必要和数据库字段相符
  * 从这个标签里可拓展嵌套查询.扩展一些其它的步骤

#### 2、collection和association的区别？

​	association 是自己对应一个的时候使用   例如 :查询学生表关联班级表

​	collection是自己对应多个的时候使用   例如 :班级表 关联了学生表

#### 3、Statement和PreparedStatement的区别？

* Statement 是没有预编译的.会有sql注入的情况
* PreparedStatement有预编译,防止sql注入.

