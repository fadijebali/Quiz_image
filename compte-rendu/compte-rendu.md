# Compte Rendu - FADI JEBALI - TP Quiz en Images

## 1. Présentation du projet
Ce projet consiste en la réalisation d'une application Android de Quiz en images développée en **Kotlin** avec une interface déclarée en **XML**. Le thème choisi est celui des **formes géométriques de base**.

- **Étudiant :** FADI JEBALI
- **Thème :** Formes géométriques (Triangle, Carré, Cercle, Rectangle, Étoile).

## 2. Interface et Widgets utilisés
L'interface a été conçue pour être responsive et accessible, en utilisant un `ScrollView` pour supporter les petits écrans. Les principaux widgets utilisés sont :
- **TextView** : Pour afficher dynamiquement le score, le numéro de question, l'énoncé et le feedback (Juste/Faux).
- **ProgressBar** : Une barre horizontale (`style="?android:attr/progressBarStyleHorizontal"`) qui suit l'avancement du quiz (nombre de réponses données).
- **ImageView** : Affiche l'image vectorielle de la forme à identifier.
- **Button** : Trois boutons pour les choix de réponse, un bouton "Suivant" et un bouton "Rejouer" en fin de partie.
- **LinearLayout** : Utilisé pour structurer les différentes zones (Quiz vs Résultat) qui sont masquées ou affichées programmatiquement.

## 3. Structure des données et questions
Les questions sont modélisées par une `data class Question` :
```kotlin
data class Question(
    val text: String,
    val imageResId: Int,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val contentDescription: String
)
```
Cinq questions statiques sont déclarées dans un tableau Kotlin. L'association des images se fait via les identifiants de ressources `R.drawable.triangle`, `R.drawable.square`, etc.

## 4. Réponses aux questions techniques

### Comment représentez-vous une question et sa bonne réponse ?
Chaque question est une instance de la classe `Question`. La bonne réponse est identifiée par la propriété `correctAnswerIndex`, qui est l'entier (0, 1 ou 2) correspondant à l'index de la chaîne de caractères correcte dans la liste `options`.

### Comment le clic sur un bouton permet-il de savoir quel choix a été sélectionné ?
Dans le code Kotlin, chaque bouton de choix possède un `setOnClickListener` qui appelle la fonction `checkAnswer(index)`. L'indice passé (0, 1 ou 2) permet d'identifier quel bouton a été pressé et de le comparer à la solution.

### Comment empêchez-vous l’attribution de plusieurs points pour une question ?
Nous utilisons deux mécanismes :
1. Une variable booléenne `isAnswered` est passée à `true` dès le premier clic. La fonction `checkAnswer` vérifie cet état avant tout traitement.
2. Les trois boutons de choix sont désactivés physiquement via `isEnabled = false` dès qu'une réponse est donnée.

### Quelle différence faites-vous entre le score et la progression ?
- **Le Score** : Mesure la performance. Il n'augmente que si l'utilisateur sélectionne la bonne réponse (Points / 5).
- **La Progression** : Mesure l'avancement. Elle augmente à chaque fois qu'une réponse est validée, qu'elle soit juste ou fausse, via la `ProgressBar`.

### Comment détectez-vous la dernière question et affichez-vous le résultat ?
Nous comparons l'indice de la question actuelle (`currentQuestionIndex`) à la taille du tableau de questions (`questions.size - 1`). Si l'égalité est vraie, le bouton de navigation change son texte en "Voir le résultat". Au clic, l'interface de quiz est masquée (`View.GONE`) et l'écran final de score est affiché.

## 5. Preuves de fonctionnement (Captures d'écran)
*Les images suivantes sont disponibles dans le dossier `/captures` du dépôt.*

1. **Question initiale** : ![Question initiale](../captures/01_question.png)
2. **Bonne réponse** : ![Bonne réponse](../captures/02_bonne_reponse.png)
3. **Mauvaise réponse** : ![Mauvaise réponse](../captures/03_mauvaise_reponse.png)
4. **Écran final** : ![Score final](../captures/04_score_final.png)

## 6. Difficultés rencontrées et solutions
Une difficulté majeure a été rencontrée lors de la compilation : les versions récentes des bibliothèques AndroidX demandaient un **compileSdk** de version 37. Le projet a été mis à jour dans `build.gradle.kts` pour passer du SDK 36 au **SDK 37**, ce qui a résolu les erreurs de métadonnées AAR.

## 7. Tests réalisés
- Vérification du score 5/5 avec 5 bonnes réponses.
- Vérification du score 0/5 avec 5 mauvaises réponses.
- Test de la rotation d'écran : l'état est conservé grâce à `onSaveInstanceState`.
- Test du bouton "Rejouer" : réinitialisation complète de l'activité.
