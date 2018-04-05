/**
 * 
 */
package org.merih.Model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Merih
 *
 */

@Repository
public interface BoardHistoryRepository extends JpaRepository<BoardHistory, Long> {
	List<BoardHistory>  findByBoardAndSequence(Board b, int sequence );
}
