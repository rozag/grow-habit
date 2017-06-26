package com.vsrstudio.entity.domain

data class Completion(val id: Id, val habitId: Id, val status: Status, val date: Date) :
        Comparable<Completion> {

    override fun compareTo(other: Completion): Int = when {
        date.seconds < other.date.seconds -> -1
        date.seconds == other.date.seconds -> 0
        else -> 1
    }

    enum class Status(val value: Int) {
        EMPTY(0),
        DONE(1),
        SKIP(2),
        FAIL(3);

        companion object {
            private val map = Status.values().associateBy(Status::value);
            fun fromInt(value: Int) = map[value] ?: throw IllegalArgumentException("Wrong value: $value")
        }
    }

}