#### mybatis 的设计模式

* 工厂模式

  * sqlSessionFactiory不解释

* 装饰者模式

  * Executor 中的 BaseExecutor 就有 Executor wrapper 的包装 

* 策略模式

  * StatementHandler有四个实现类都是基于配置文件来的	由 RoutingStatementHandler 来统一创建 实现的方式	

    ```java
      public RoutingStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
            switch(ms.getStatementType()) {
            case STATEMENT:
                this.delegate = new SimpleStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
                break;
            case PREPARED:
                this.delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
                break;
            case CALLABLE:
                this.delegate = new CallableStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
                break;
            default:
                throw new ExecutorException("Unknown statement type: " + ms.getStatementType());
            }
        }
    ```

* 代理模式

  * MapperProxyFactory 的代理模式 代理实际sql的实现类	

* 模板方法模式

  * Executor 的实现类BaseExecutor 其中就定义了模板的方法。具体的方法的实现类由子类实现

