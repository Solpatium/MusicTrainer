package music_trainer.webapp

import music_trainer.scale.Exercises.{Answer, DualIntervalExcercise}
import music_trainer.scale.Track
import music_trainer.scale._

import angulate2.std._
import angulate2.platformBrowser.BrowserModule

import music_trainer.webapp.component._

@NgModule(
  imports = @@[BrowserModule],
  declarations = @@[AppComponent,ExerciseList,ExerciseItem,ExerciseView,AnswersList,AnswerBox],
  bootstrap = @@[AppComponent]
)
class AppModule {

}

@Component(
  selector = "my-app",
  templateUrl = "resources/app.html"
)
class AppComponent {

  @ViewChild("ev")
  var exerciseView: ExerciseView = _

  var num = 0

  def returnFromExercise(code: Int){
    num = 0
    exerciseView.stopExercise()
  }

  def menuSel(item: SelectedOption){
    num = item.id
    exerciseView.changeExercise(num, item.numOfExercises)
  }
}
