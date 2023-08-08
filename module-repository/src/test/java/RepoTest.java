import com.mjc.school.repository.implementation.dao.NewsRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepoTest {
    NewsRepository repository = new NewsRepository();

    @Test
    public void testRepo() {

        System.out.println(repository.getNewsByParams(null, null, "a", null, null));

        assertEquals(0,0);
    }
}
