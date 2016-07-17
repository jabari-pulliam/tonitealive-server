package com.tonitealive.server.domain

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity
class UserProfile {

    @GraphId var id: Long? = null
    var username: String? = null

}