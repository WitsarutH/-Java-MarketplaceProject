package ku.cs.shop.services;

import java.io.IOException;

public interface DataSource<T> {
    T readData();
    void writeData(T t) throws IOException;

}
