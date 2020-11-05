package io.github.astrapi69.gambleboom.jpa.entities;

import de.alpharogroup.db.entity.processable.Processable;
import de.alpharogroup.db.entity.uniqueable.UUIDEntity;
import de.alpharogroup.db.entity.verifiable.Verifiable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity @Table(name = "drawn_numbers")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Draws
	extends UUIDEntity implements Verifiable, Processable
{

	/**
	 * The drawn date of this drawn numbers.
	 */
	@Column LocalDateTime drawnDate;

	/**
	 * The set with the drawn lottery numbers.
	 */
	@ElementCollection
	@CollectionTable(name = "drawn_numbers_lottery_numbers", joinColumns = @JoinColumn(name = "drawn_numbers_id", foreignKey = @ForeignKey(name = "fk_drawn_numbers_id")))
	@Column(name = "lottery_number")
	Set<Integer> lotteryNumbers;

	@Column String signature;

	@Column boolean processable;

}
