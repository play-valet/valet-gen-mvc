package module.twirlScaffoldThemes.generates._common

import org.valet.common.ScaffoldDtos

trait ValetProcessCycle {

  def initAction(dtos: ScaffoldDtos)
  def mainAction(dtos: ScaffoldDtos)
  def endAction(dtos: ScaffoldDtos)


}
