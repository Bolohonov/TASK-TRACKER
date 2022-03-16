package repository;

import org.junit.jupiter.api.Test;

public interface TaskUpdaterTest {

    @Test
    void updateName();

    @Test
    void updateDescription();

    @Test
    void updateStatus();
}