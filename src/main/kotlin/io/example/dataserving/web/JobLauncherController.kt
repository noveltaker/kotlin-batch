package io.example.dataserving.web

import io.example.dataserving.job.dto.MsgDTO
import io.example.dataserving.job.incrementer.DateIncrementer
import org.springframework.batch.core.Job
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("batch")
class JobLauncherController constructor(
    private val jobLauncher: JobLauncher,
    @Qualifier("simpleJob4")
    private val job: Job,
    private val dateIncrementer: DateIncrementer
) {

    @PostMapping("csv")
    fun runCsvJob(): MsgDTO {

        val jobParameters = dateIncrementer.getNext(null)

        jobLauncher.run(job, jobParameters)

        return MsgDTO(HttpStatus.OK.value(), "SUCCESS")
    }
}
