# Compte Rendu - FADI JEBALI  (tp Quiz )

## Présentation
Ce projet est une application de quiz composée de 5 questions illustrées sur le thème des formes géométriques. L'utilisateur doit identifier des formes (triangle, carré, cercle, etc.) à partir d'images.

## Interface et Widgets
L'interface est définie dans `activity_main.xml` et utilise :
- **TextView** : Pour le titre, le score, le numéro de question et le texte de la question.
- **ProgressBar** : Pour afficher l'avancement dans le quiz (nombre de réponses données).
- **ImageView** : Pour afficher l'image associée à chaque question.
- **Button** : Trois boutons pour les choix de réponse, un bouton de navigation et un bouton pour rejouer.
- **ScrollView** : Pour assurer que l'interface reste lisible sur tous les types d'écrans.
- **LinearLayout** : Pour organiser les zones de jeu et de résultats.

## Structure des Questions
Les questions sont stockées dans un tableau Kotlin d'objets `Question` (data class) :
- `text`: Texte de la question.
- `imageResId`: Identifiant drawable (ex: `R.drawable.triangle`).
- `options`: Liste de 3 chaînes de caractères.
- `correctAnswerIndex`: Entier (0, 1 ou 2) pour identifier la bonne réponse.
- `contentDescription`: Texte pour l'accessibilité.

## Logique de l'Application
1. **Événements** : Le clic sur un bouton de choix déclenche la vérification de la réponse. Si l'indice correspond à `correctAnswerIndex`, le score est incrémenté.
2. **Progression** : La barre de progression avance à chaque réponse donnée (+1), quel que soit le résultat.
3. **Score** : Le score est mis à jour dynamiquement dans le bandeau supérieur.
4. **Navigation** : Après chaque réponse, les boutons sont désactivés pour éviter les clics multiples, et un bouton de navigation apparaît.
5. **Fin de jeu** : À la dernière question, le bouton affiche "Voir le résultat" et bascule vers l'écran final affichant le score sur 5.

## Tests réalisés
- Lancement de partie (tout à zéro).
- Validation de réponse juste vs fausse (score et progression).
- Bloquage des boutons après réponse.
- Rotation d'écran (conservation du score et de l'état).
- Réinitialisation via le bouton "Rejouer".
