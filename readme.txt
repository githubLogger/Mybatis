步骤：
    ①加依赖
    ②配置数据源信息
    ③编写实体类、mapper接口、mapper.xml
    ④创建SqlSessionFactory调用openSession方法获取session。调用session.getMapper(UserMapper.class);获取mapper对象，执行相应的方法
查询到的列名和resultType指定的pojo的属性名一致，才能映射成功。
如果查询到的列名和映射的pojo的属性名不一致时，通过resultMap设置列名和属性名之间的对应关系（映射关系）。可以完成映射。
在编写mapper.xml(映射文件)和mapper.java需要遵循一个开发规范：
    1、mapper.xml中namespace就是mapper.java的类全路径。
    2、mapper.xml中statement的id和mapper.java中方法名一致。
    3、mapper.xml中statement的parameterType指定输入参数的类型和mapper.java的方法输入参数类型一致。
    4、mapper.xml中statement的resultType指定输出结果的类型和mapper.java的方法返回值类型一致。
resultMap使用association和collection完成一对一和一对多高级映射（对结果有特殊的映射要求）。
    association:
        作用：将关联查询信息映射到一个pojo对象中。
        场合：为了方便查询关联信息可以使用association将关联订单信息映射为用户对象的pojo属性中，比如：查询订单及关联用户信息。
　　collection：
        作用：将关联查询信息映射到一个list集合中。
        场合：为了方便查询遍历关联信息可以使用collection将关联信息映射到list集合中，比如：查询用户权限范围模块及模块下的菜单，
                  可使用collection将模块映射到模块list中，将菜单列表映射到模块对象的菜单list属性中，这样的作的目的也是方便对查询结果集进行遍历查询。


一级缓存：
    Mybatis的一级缓存是指SqlSession。一级缓存的作用域是一个SqlSession。Mybatis默认开启一级缓存。
    在同一个SqlSession中，执行相同的查询SQL，第一次会去查询数据库，并写到缓存中；第二次直接从缓存中取。
    当执行SQL时两次查询中间发生了增删改操作，则SqlSession的缓存清空。
    Mybatis的内部缓存使用一个HashMap，key为hashcode+statementId+sql语句。Value为查询出来的结果集映射成的java对象。
    SqlSession执行insert、update、delete等操作commit后会清空该SQLSession缓存。
二级缓存：
    Mybatis的二级缓存是指mapper映射文件。二级缓存的作用域是同一个namespace下的mapper映射文件内容，多个SqlSession共享。Mybatis需要手动设置启动二级缓存。
    在同一个namespace下的mapper文件中，执行相同的查询SQL，第一次会去查询数据库，并写到缓存中；第二次直接从缓存中取。当执行SQL时两次查询中间发生了增删改操作，则二级缓存清空。
    第一次调用mapper下的SQL去查询用户信息。查询到的信息会存到该mapper对应的二级缓存区域内。
    第二次调用相同namespace下的mapper映射文件中相同的SQL去查询用户信息。会去对应的二级缓存内取结果。
    如果调用相同namespace下的mapper映射文件中的增删改SQL，并执行了commit操作。此时会清空该namespace下的二级缓存。
开启二级缓存、懒加载：
    ①<!--开启二级缓存、 开启延迟加载 -->
    <settings>
        <!-- lazyLoadingEnabled:延迟加载启动，默认是false -->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!-- aggressiveLazyLoading：积极的懒加载，false的话按需加载，默认是true -->
        <setting name="aggressiveLazyLoading" value="false"/>
        <!-- 开启二级缓存，默认是false -->
        <setting name="cacheEnabled" value="true"/>
    </settings>
    ②在映射文件中，加入<cache />标签，开启二级缓存：
        注：由于二级缓存的数据不一定都是存储到内存中，它的存储介质多种多样，所以需要给缓存的对象执行序列化。
        如果该类存在父类，那么父类也要实现序列化。implement Serializable
        statement中设置userCache=false可以禁用当前select语句的二级缓存，即每次查询都是去数据库中查询，默认情况下是true，即该statement使用二级缓存。
