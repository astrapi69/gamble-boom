package io.github.astrapi69.gambleboom.jpa.entities;

import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.identifiable.Identifiable;
import de.alpharogroup.db.entity.verifiable.Verifiable;
import de.alpharogroup.sign.annotation.SignatureExclude;
import io.github.astrapi69.gambleboom.jpa.listeners.DrawsSignatureListener;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity @Table(name = Draws.TABLE_NAME)
@EntityListeners({ DrawsSignatureListener.class })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Draws implements Verifiable
{
	static final String SINGULAR_ENTITY_NAME = "draw";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_LOTTERY_NUMBER = "lottery_number";
	static final String COLLECTION_TABLE_NAME = "draws_lottery_numbers";
	static final String JOIN_COLUMN_NAME = SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String COLLECTION_TABLE_FOREIGN_KEY = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ TABLE_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	@Id
	@GeneratedValue(
		generator = "UUID"
	)
	@Column(
		name = "id",
		updatable = false,
		nullable = false
	) private UUID id;
	/**
	 * The drawn date of this drawn numbers.
	 */
	@Column LocalDateTime drawnDate;

	/**
	 * The set with the drawn lottery numbers.
	 */
	@ElementCollection
	@CollectionTable(name = "draws_lottery_numbers", joinColumns = @JoinColumn(name = "draw_id", foreignKey = @ForeignKey(name = "fk_draws_id")))
	@Column(name = "lottery_number")
	Set<Integer> lotteryNumbers;

	@SignatureExclude
	@Column String signature;

}
