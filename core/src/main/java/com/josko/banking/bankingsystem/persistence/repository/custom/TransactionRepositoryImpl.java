package com.josko.banking.bankingsystem.persistence.repository.custom;

import com.josko.banking.bankingsystem.persistence.entity.Account_;
import com.josko.banking.bankingsystem.persistence.entity.Customer_;
import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import com.josko.banking.bankingsystem.persistence.entity.Transaction_;
import com.josko.banking.bankingsystem.persistence.repository.TransactionRepository;
import com.josko.banking.bankingsystem.persistence.repository.criteria.TransactionRepoCriteria;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class TransactionRepositoryImpl implements FindByCriteria<TransactionRepoCriteria, Transaction> {

	private final TransactionRepository repository;

	public TransactionRepositoryImpl(@Lazy TransactionRepository repository) {
		this.repository = repository;
	}

	
	@Override
	public List<Transaction> findByCriteria(TransactionRepoCriteria criteria) {
		
		if (criteria == null)
			return repository.findAll();

		var specification = getSpecification(criteria);

		return repository.findAll(specification);
	}
	

	private static Specification<Transaction> getSpecification(TransactionRepoCriteria criteria) {
		return Specification
				.where(greaterThanOrEqualAmount(criteria.fromAmount()))
				.and(lessThanOrEqualAmount(criteria.toAmount()))
				.and(likeMessage(criteria.message()))
				.and(eqSenderId(criteria.senderId()))
				.and(eqReceiverId(criteria.senderId()));
	}

	private static Specification<Transaction> greaterThanOrEqualAmount(Double amount) {
		return amount == null ? null :
				(root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Transaction_.amount), amount);
	}

	private static Specification<Transaction> lessThanOrEqualAmount(Double amount) {
		return amount == null ? null :
				(root, query, cb) -> cb.lessThanOrEqualTo(root.get(Transaction_.amount), amount);
	}

	private static Specification<Transaction> likeMessage(String message) {
		return isBlank(message) ? null :
				(root, query, cb) -> cb.like( cb.lower(root.get(Transaction_.message)),
				"%" + message.toLowerCase() + "%" );
	}

	private static Specification<Transaction> eqSenderId(Long id) {
		return id == null ? null :
				(root, query, cb) -> cb.equal(
						root.get(Transaction_.senderAccount).get(Account_.customer).get(Customer_.customerId),
						id);
	}

	private static Specification<Transaction> eqReceiverId(Long id) {
		return id == null ? null :
				(root, query, cb) -> cb.equal(
						root.get(Transaction_.receiverAccount).get(Account_.customer).get(Customer_.customerId),
						id);
	}
}
