package com.srini

/**
 * @author ${user.name}
 */
object App {
  
  def main(args : Array[String]) {
    println( Converter.encrypt("SRINIABC"))
    println(Converter.decrypt("373rPdtMfK3M6wr+I673CQ=="))
  }

}
