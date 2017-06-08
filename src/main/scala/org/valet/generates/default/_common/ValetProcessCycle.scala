package org.valet.generates.default._common

import org.valet.common.ScaffoldDtos

private[valet] trait ValetProcessCycle {

  def initAction(dtos: ScaffoldDtos)
  def mainAction(dtos: ScaffoldDtos)
  def endAction(dtos: ScaffoldDtos)


}
