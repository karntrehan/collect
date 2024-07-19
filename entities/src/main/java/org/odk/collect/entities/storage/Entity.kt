package org.odk.collect.entities.storage

sealed interface Entity {
    val list: String
    val id: String
    val label: String?
    val version: Int
    val properties: List<Pair<String, String>>
    val state: State

    data class New(
        override val list: String,
        override val id: String,
        override val label: String?,
        override val version: Int = 1,
        override val properties: List<Pair<String, String>> = emptyList(),
        override val state: State = State.OFFLINE
    ) : Entity

    data class Saved(
        override val list: String,
        override val id: String,
        override val label: String?,
        override val version: Int = 1,
        override val properties: List<Pair<String, String>> = emptyList(),
        override val state: State = State.OFFLINE,
        val index: Int
    ) : Entity

    enum class State {
        OFFLINE,
        ONLINE
    }

    fun sameAs(entity: Entity): Boolean {
        val a = convertToNew(this)
        val b = convertToNew(entity)
        return a == b
    }

    private fun convertToNew(entity: Entity): New {
        return New(
            entity.list,
            entity.id,
            entity.label,
            entity.version,
            entity.properties,
            entity.state
        )
    }
}