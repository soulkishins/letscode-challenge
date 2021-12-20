package br.com.letscode.starwars.socialnetwork.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_traitor_report")
@SequenceGenerator(name = "sq_traitor_report", allocationSize = 1)
public class TraitorReport {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_traitor_report")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reporter_id", nullable = false, updatable = false, referencedColumnName = "id")
	private Rebel reporter;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "traitor_id", nullable = false, updatable = false, referencedColumnName = "id")
	private Rebel traitor;

}
