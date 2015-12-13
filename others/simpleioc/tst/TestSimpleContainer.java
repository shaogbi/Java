import com.shaogbi.ioc.SimpleContainer;
import com.shaogbi.ioc.teststuff.BookDao;
import com.shaogbi.ioc.teststuff.ISampleInterface;
import com.shaogbi.ioc.teststuff.SelfreportRunner;

public class TestSimpleContainer {
    public static void main(String[] args) {
        SimpleContainer container = new SimpleContainer();
        container.registerBean(SelfreportRunner.class);
        SelfreportRunner selfreportRunner = container.getBean(SelfreportRunner.class);
        selfreportRunner.selfreport();
        
        SelfreportRunner selfreportRunner2 = container.getBean(SelfreportRunner.class);
        System.out.println(selfreportRunner.hashCode());
        System.out.println(selfreportRunner2.hashCode()); // these 2 hash code are same because of singleton
        
        BookDao bookDao = container.getBean(BookDao.class);
        System.out.println("BookDao is " + bookDao); // should be null because it is not registered
        
        container.registerBean(ISampleInterface.class);
    }
}
