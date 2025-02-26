package programmerzamannow.jpa.entity;

import java.time.LocalDateTime;

public interface UpdateAtAware {

    void setUpdatedAt(LocalDateTime localDateTime);
}
