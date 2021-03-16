package br.avcaliani.spring.batch.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBatchAppApplication

fun main(args: Array<String>) {
	runApplication<SpringBatchAppApplication>(*args)
}
