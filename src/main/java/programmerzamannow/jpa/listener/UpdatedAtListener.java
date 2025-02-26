package programmerzamannow.jpa.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import programmerzamannow.jpa.entity.UpdateAtAware;

import java.time.LocalDateTime;

public class UpdatedAtListener {

    @PrePersist
    @PreUpdate
    public void setLastUpdatedAt(UpdateAtAware object) {
        object.setUpdatedAt(LocalDateTime.now());
    }
}
