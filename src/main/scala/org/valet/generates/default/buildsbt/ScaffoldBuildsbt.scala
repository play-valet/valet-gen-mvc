package org.valet.generates.default.buildsbt

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle

object ScaffoldBuildsbt extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos) = {

  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    DefBuildsbt.addLibraryDependencies(dtos)
  }

  override def endAction(dtos: ScaffoldDtos) = {

  }

}
