package com.artemissoftware.orpheusplaylist.data.models

data class TrackPositionMetadata(
    val track: Int,
    val disc: Int,
){
    companion object {
        const val defaultDisc = 1
    }
}
