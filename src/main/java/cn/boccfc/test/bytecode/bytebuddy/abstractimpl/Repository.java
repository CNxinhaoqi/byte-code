package cn.boccfc.test.bytecode.bytebuddy.abstractimpl;

public abstract class Repository<T> {
    public abstract T queryData(int id);
}
