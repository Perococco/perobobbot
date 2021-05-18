package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.*;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@MappedSuperclass
@NoArgsConstructor
@Setter @Getter
public class BotEntityBase extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity owner;

    @Column(name = "NAME",nullable = false,unique = true)
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
