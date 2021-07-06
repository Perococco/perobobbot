package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.BotExtensionEntity;
import perobobbot.data.domain.JoinedChannelEntity;
import perobobbot.data.domain.UserEntity;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Setter @Getter
public class BotEntityBase extends PersistentObjectWithUUID {

    public static final String USER_ID_COLUMN_NAME = "USER_ID";
    public static final String NAME_COLUMN_NAME = "NAME";

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN_NAME, nullable = false)
    private UserEntity owner;

    @Column(name = NAME_COLUMN_NAME,nullable = false)
    @NotBlank
    @Setter
    private String name;

    @OneToMany(mappedBy = "bot",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinedChannelEntity> joinedChannels = new ArrayList<>();

    @OneToMany(mappedBy = "bot",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BotExtensionEntity> botExtensions = new ArrayList<>();

    public BotEntityBase(UserEntity owner, @NonNull String name) {
        super(UUID.randomUUID());
        this.owner = owner;
        this.name = name;
    }

}
