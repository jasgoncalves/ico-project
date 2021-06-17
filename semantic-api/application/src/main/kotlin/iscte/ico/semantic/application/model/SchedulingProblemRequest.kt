package iscte.ico.semantic.application.model

data class SchedulingProblemRequest (

    val name : String,
    val schedulingProblem : SchedulingProblem,
    val machines : Array<Machine>,
    val order : Order,
    val jobFamilies: Array<JobFamily>,
    val jobs : Array<Job>,
    val tasks : Array<Task>,
    val objectiveFunction : ObjectiveFunction

)