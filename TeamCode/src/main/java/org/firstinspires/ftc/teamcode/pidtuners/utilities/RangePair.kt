package org.firstinspires.ftc.teamcode.pidtuners.utilities

data class RangePair<A>(val low:A,val high:A){
    fun output():String{
        return "RangePair(low=$low, high=$high)"
    }
}