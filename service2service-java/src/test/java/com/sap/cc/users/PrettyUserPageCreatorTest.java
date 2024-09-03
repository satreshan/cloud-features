package com.sap.cc.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sap.cc.ascii.AsciiArtServiceClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrettyUserPageCreatorTest {

  @Mock
  private AsciiArtServiceClient asciiArtServiceClient;

  @InjectMocks
  private PrettyUserPageCreator prettyUserPageCreator;

  @Test
  void shouldCreateAPrettyUserPage() {
    User user = new User("someName", "somePhoneNumber", "1");

    when(asciiArtServiceClient.getAsciiString(any())).thenReturn("prettifiedName");
    assertThat(prettyUserPageCreator.getPrettyPage(user)).isEqualTo(
            "prettifiedName" + "\r\n" + user.getPhoneNumber());
  }
}