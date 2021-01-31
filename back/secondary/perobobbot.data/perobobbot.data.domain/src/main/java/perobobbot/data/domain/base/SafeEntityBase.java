package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.com.NotEnoughPoints;
import perobobbot.data.domain.TransactionEntity;
import perobobbot.lang.PointType;
import perobobbot.lang.Platform;
import perobobbot.lang.Safe;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class SafeEntityBase extends PersistentObjectWithUUID {


    @Column(name = "PLATFORM", nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull Platform platform;

    @OneToMany(mappedBy = "target",cascade = CascadeType.ALL,orphanRemoval = true)
    private @NonNull List<TransactionEntity> transactions = new ArrayList<>();

    @Column(name = "CHANNEL_NAME", nullable = false)
    private @NonNull String channelName;

    @Column(name = "USER_CHAT_ID", nullable = false)
    private @NonNull String userChatId;

    @Column(name = "POINT_TYPE", nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull PointType type;

    @Column(name = "AMOUNT")
    private @NonNull long amount;

    public SafeEntityBase(@NonNull Platform platform, @NonNull String channelName, @NonNull String userChatId, @NonNull PointType type) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.channelName = channelName;
        this.userChatId = userChatId;
        this.type = type;
        this.amount = 0;
    }

    public void checkEnoughBalance(long requestedAmount) {
        if (this.amount < requestedAmount) {
            throw new NotEnoughPoints(getPlatform(), getChannelName(), getUserChatId(), getType(), requestedAmount);
        }
    }

    public void performWithdraw(long requestedAmount) {
        this.checkEnoughBalance(requestedAmount);
        this.amount -= requestedAmount;
    }

    public @NonNull Safe toView() {
        return Safe.builder()
                   .id(getUuid())
                   .platform(platform)
                   .channelName(channelName)
                   .userChatId(userChatId)
                   .type(type)
                   .build();
    }

    public void addToAmount(int amountToAdd) {
        this.amount += amountToAdd;
    }
}
