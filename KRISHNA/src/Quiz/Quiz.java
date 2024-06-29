package Quiz;

import java.util.*;

class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

class Quiz {
    private List<Question> questions;
    private int score;
    private Map<Integer, Boolean> answers;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.score = 0;
        this.answers = new HashMap<>();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            List<String> options = question.getOptions();
            for (int j = 0; j < options.size(); j++) {
                System.out.println((j + 1) + ". " + options.get(j));
            }

            long startTime = System.currentTimeMillis();
            int userAnswer = -1;
            boolean answered = false;

            while (!answered) {
                if ((System.currentTimeMillis() - startTime) / 1000 > 10) { // 10-second timer
                    System.out.println("Time's up!");
                    break;
                }
                if (scanner.hasNextInt()) {
                    userAnswer = scanner.nextInt() - 1;
                    answered = true;
                }
            }

            if (answered && userAnswer == question.getCorrectAnswerIndex()) {
                score++;
                answers.put(i, true);
            } else {
                answers.put(i, false);
            }

            System.out.println();
        }

        displayResults();
    }

    private void displayResults() {
        System.out.println("Quiz over!");
        System.out.println("Your score: " + score + " out of " + questions.size());

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            System.out.println("Correct answer: " + question.getOptions().get(question.getCorrectAnswerIndex()));
            if (answers.get(i)) {
                System.out.println("Your answer: " + question.getOptions().get(question.getCorrectAnswerIndex()) + " (Correct)");
            } else {
                System.out.println("Your answer: Incorrect or not answered");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<Question> questions = Arrays.asList(
            new Question("When india got independence ? ", Arrays.asList("1950", "1946", "1947", "1948"), 2),
            new Question("What is the meaning of synonym of 'effort' ?", Arrays.asList("Try", "Forgive", "Before", "Seldom"), 0),
            new Question("Where is the tallest building of World ?", Arrays.asList("America", "China", "Dubai", "Russia"), 2),
            new Question("Who wrote the 'Ramayana?", Arrays.asList("Tulsidas", "Valmiki", "Vyasa", "Vishwamitra"), 1)
        );

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}

