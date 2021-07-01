package reproducer;

import org.apache.camel.component.leveldb.LevelDBAggregationRepository;

public class MyLevelDbRepo extends LevelDBAggregationRepository {

    public MyLevelDbRepo() {
        super("test");
        setPersistentFileName("../common/target/leveldb.dat");
        setReturnOldExchange(true);
    }
}
