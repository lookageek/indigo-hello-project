import indigo.*
import indigo.shared.config.GameViewport
import indigo.shared.datatypes.RGBA

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object HelloIndigo extends IndigoSandbox[Unit, Model] {

  val config: GameConfig =
    GameConfig.default
      .withViewport(GameViewport(720, 480))

  val animations: Set[Animation] =
    Set()

  val assetName = AssetName("dots")

  val assets: Set[AssetType] =
    Set(AssetType.Image(assetName, AssetPath("assets/dots.png")))

  val fonts: Set[FontInfo] =
    Set()

  val shaders: Set[Shader] =
    Set()

  def setup(
      assetCollection: AssetCollection,
      dice: Dice
  ): Outcome[Startup[Unit]] =
    Outcome(Startup.Success(()))

  def initialModel(startupData: Unit): Outcome[Model] =
    Outcome(Model.initial)

  def updateModel(
      context: FrameContext[Unit],
      model: Model
  ): GlobalEvent => Outcome[Model] = {
    case e: MouseEvent.Click => Outcome(model.update)
    case _ => Outcome(model)
  }

  def present(
      context: FrameContext[Unit],
      model: Model
  ): Outcome[SceneUpdateFragment] = {
    val graphic = model.counter % 4 match {
      case 0 => Graphic(
        Rectangle(0, 0, 32, 32), 1, Material.Bitmap(assetName)
      ).withCrop(Rectangle(0, 0, 16, 16))
      case 1 =>
        Graphic(
          Rectangle(0, 0, 32, 32), 1, Material.Bitmap(assetName)
        ).withCrop(Rectangle(0, 16, 16, 16))
      case 2 =>
        Graphic(
          Rectangle(0, 0, 32, 32), 1, Material.Bitmap(assetName)
        ).withCrop(Rectangle(16, 0, 16, 16))
      case 3 =>
        Graphic(
          Rectangle(0, 0, 32, 32), 1, Material.Bitmap(assetName)
        ).withCrop(Rectangle(16, 16, 16, 16))
    }

    Outcome(SceneUpdateFragment(
      graphic.moveTo(config.viewport.center)
    ))
  }
}
