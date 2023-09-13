package com.android.bloomshows.network.model.mapper

interface EntityMapper<Domain, Entity> {

  fun asEntity(domain: Domain,category: String): Entity

  fun asDomain(entity: Entity): Domain
}
