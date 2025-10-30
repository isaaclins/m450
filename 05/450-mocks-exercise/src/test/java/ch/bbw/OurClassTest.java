package ch.bbw;

import ch.bbw.otherpackage.Person;
import ch.bbw.otherpackage.SlowClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OurClassTest {

    @Mock
    SlowClass slowClass;

    @InjectMocks
    OurClass ourClass;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSlowString() throws Exception {
        when(slowClass.slowString()).thenReturn("mock result");

        String result = ourClass.getSlowString();

        assertEquals("mock result", result, "getSlowString should return value from mock");
        verify(slowClass).slowString();
    }

    @Test
    void getSlowStringStars() throws Exception {
        when(slowClass.slowStringParameter(anyInt())).thenReturn("mock stars");

        String result = ourClass.getSlowStringStars(4);

        assertEquals("mock stars", result, "getSlowStringStars should return value from mock");
        verify(slowClass).slowStringParameter(4);
    }

    @Test
    void addJoeSmith() throws Exception {
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

        ourClass.addJoeSmith(1990, 5, 10);

        verify(slowClass).slowSave(personCaptor.capture());
        Person saved = personCaptor.getValue();

        assertEquals("Joe", saved.firstname());
        assertEquals("Smith", saved.lastname());
        assertEquals(LocalDate.of(1990, 5, 10), saved.birthdate());
    }

}
