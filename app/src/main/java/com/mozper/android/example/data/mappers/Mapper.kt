package vc.reboot.nova.mobile.sabadell.core.repository.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}
