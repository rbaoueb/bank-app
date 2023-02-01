package fr.harvest.vic.espacesuivi.api.espacesuivi.api.collector;

import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.PieceDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.StepDTO;
import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class PieceDtoCollectorTest extends TestCase
{
	public void testAccept_true()
	{
		PieceDtoCollector pieceDtoCollector = new PieceDtoCollector();
		pieceDtoCollector.setNbWaitingPieces(0);
		PieceDTO documentDTO = new PieceDTO();
		documentDTO.setConforme(true);
		documentDTO.setDateReception("01/01/2021");
		pieceDtoCollector.accept(documentDTO);

		assertThat(pieceDtoCollector.getNbWaitingPieces()).isEqualTo(0);
	}

	public void testAccept_false()
	{
		PieceDtoCollector pieceDtoCollector = new PieceDtoCollector();
		pieceDtoCollector.setNbWaitingPieces(0);
		PieceDTO documentDTO = new PieceDTO();
		documentDTO.setConforme(true);
		pieceDtoCollector.accept(documentDTO);

		assertThat(pieceDtoCollector.getNbWaitingPieces()).isEqualTo(1);
	}

	public void testMerge()
	{
		PieceDtoCollector pieceDtoCollector = new PieceDtoCollector();
		pieceDtoCollector.setNbWaitingPieces(5);
		pieceDtoCollector.setState(StepDTO.StateEnum.ACCEPTED);
		PieceDtoCollector pieceDtoCollector2 = new PieceDtoCollector();
		pieceDtoCollector2.setState(StepDTO.StateEnum.REJECTED);
		pieceDtoCollector2.setNbWaitingPieces(8);

		pieceDtoCollector.merge(pieceDtoCollector2);

		assertThat(pieceDtoCollector.getNbWaitingPieces()).isEqualTo(13);
		assertThat(pieceDtoCollector.getState()).isEqualTo(StepDTO.StateEnum.REJECTED);
	}
}