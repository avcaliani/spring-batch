package br.avcaliani.spring.batch.app.job.csv.parser.processor

import br.avcaliani.spring.batch.app.job.csv.parser.model.User
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class UserItemProcessor : ItemProcessor<User, User> {

    private val log = LoggerFactory.getLogger(UserItemProcessor::class.java)

    override fun process(item: User): User {
        val updatedUser = User(item.firstName.toUpperCase(), item.lastName.toUpperCase())
        log.info("Converting '{}' into '{}'", item, updatedUser);
        return updatedUser
    }
}