package com.mlambo.coacalculator

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mlambo.coacalculator.ui.theme.CoaCalculatorTheme
import java.time.LocalDate
import java.time.Period
import kotlin.math.round
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val michael = Student("Michael", 120, 3.97)
            Log.d("ML", "Michael is $michael")

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                HeaderXU()
                CostOverview()
                CostOfAttendance()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header() {
    Column(modifier = Modifier
        .background(color = Color.White)
        .fillMaxWidth()
        .padding(40.dp)
    ) {
        DisplayTitleCase(name = "Xavier university oF LA", birthDate = LocalDate.of(1925, 10, 6))

        Text(
            text = "Managing Cost",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Cost & Tuition Rates",
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HeaderPreview() {
    CoaCalculatorTheme {
        Header();
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayTitleCase(name: String, birthDate: LocalDate) {
    val titleCaseName = name.lowercase().split(" ").joinToString(" ") {
        it.replaceFirstChar { char -> char.uppercase() }
    }

    val age = Period.between(birthDate, LocalDate.now()).years

    println("$titleCaseName is $age years old.")
    Log.d("ML", "$titleCaseName is $age years old.")

    Text(text = "$titleCaseName is $age years old.")
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TitleCasePreview(){
    CoaCalculatorTheme {
        DisplayTitleCase(name = "Xavier university oF LA", birthDate = LocalDate.of(1925, 10, 6));
    }
}

@Composable
fun DisplayTopStudent(){
    val student1 = Student("Michael", 12, 3.97)
    val student2 = Student("Jake", 18, 3.65)
    val student3 = Student("Marcel", 15, 3.70)

    val students = listOf(student1, student2, student3)
    var highestGpaStudent = student1

    for (student in students){
        if (student.gpa > highestGpaStudent.gpa){
            highestGpaStudent = student
        }
    }

    Text (text = "${highestGpaStudent.firstName} has the highest GPA with a GPA of ${highestGpaStudent.gpa}")
}

@Preview
@Composable
fun DisplayTopStudentPreview(){
    CoaCalculatorTheme {
        DisplayTopStudent()
    }
}

@Composable
fun DisplayTopStudent200(){
    val students = mutableListOf(Student("Michael", 12, 3.97))

    for (i in 1..200){
        val gpa = Random.nextDouble(1.00, 4.00)
        val roundedGpa = round(gpa * 100) / 100
        val student = Student ("Student ${i}", Random.nextInt(12, 121), roundedGpa)

        students.add(student)
    }

    Column {
        Text(text = "The students with GPA > 3.5:")

        val sortedStudents = students.sortedBy { student: Student -> student.gpa }

        for (student in sortedStudents) {
            if (student.gpa > 3.5) {
                Text(text = "${student.firstName} - ${student.gpa}")
            }
        }
    }
}

@Preview
@Composable
fun DisplayTopStudent200Preview(){
    CoaCalculatorTheme {
        DisplayTopStudent200()
    }
}

@Composable
fun EnterName(){
    var text by remember { mutableStateOf("Hello") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Enter your fullname") }
    )
}

@Preview
@Composable
fun EnterNamePreview(){
    EnterName()
}

@Composable
fun SemestersAsRadioButtons(selectedOption: String,
                            onOptionSelected: (String) -> Unit){
    val radioOptions = listOf("Both Fall and Spring 2024-2025", "Fall", "Spring", "Summer")

    Column(Modifier.selectableGroup()) {
        Text(
            text = "Semesters",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun SemestersAsDropdowns(){
    var expanded by remember { mutableStateOf(false) }
    var selectedSemester by remember { mutableStateOf("Both Fall and Spring 2024-2025") }

    Column {
        Text(
            text = "Semesters",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .clickable { expanded = true }
            .padding(start = 16.dp, end = 16.dp, top = 6.dp)
        ) {
            Text(
                text = selectedSemester,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DropdownMenuItem(
                    text = { Text("Both Fall and Spring 2024-2025") },
                    onClick = {
                        selectedSemester = "Both Fall and Spring 2024-2025"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Fall 2024") },
                    onClick = {
                        selectedSemester = "Fall 2024"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Spring 2025") },
                    onClick = {
                        selectedSemester = "Spring 2025"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Summer 2024") },
                    onClick = {
                        selectedSemester = "Summer 2024"
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun SemesterAsDropdownsPreview(){
    SemestersAsDropdowns()
}

@Composable
fun HeaderXU() {
    Column(modifier = Modifier
        .background(color = Color.White)
        .fillMaxWidth()
        .padding(30.dp)
    ) {
        Text(
            text = "COST OF ATTENDANCE BUDGETS",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
            )
        Text(
            text = "2024-2025",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun HeaderXuPreview(){
    HeaderXU()
}

@Composable
fun CostOverview() {
    Column(modifier = Modifier
        .background(color = Color.White)
        .fillMaxWidth()
        .padding(30.dp)
    ) {
        Text(
            text = "ESTIMATED COSTS FOR AN ACADEMIC YEAR",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Your Cost of Attendance is the total amount of money you may need to pay for your college expenses. You are eligible,\n" +
                    "at the maximum, for an amount of financial assistance equal to the total Cost of Attendance. We estimate your Cost of\n" +
                    "Attendance based on direct and indirect costs you may incur while attending school. A student’s actual costs may vary\n" +
                    "due to factors such as the degree program you enroll in, the number of courses you take, your housing and MEALSS plan\n" +
                    "choices, and your personal expenses.",
            modifier = Modifier.padding(bottom = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = "DIRECT COSTS",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Only a portion of your Cost of Attendance is payable directly to the school; these are direct costs. Direct costs are\n" +
                    "tuition and fees. Housing and a MEAL plan are also direct costs if you choose to live on campus. Direct costs are billed by Xavier\n" +
                    "University of Louisiana’s Office of Student Accounts. If you receive financial assistance, such as grants,\n" +
                    "scholarships, or loans, these funds will go directly into your student account until the direct costs are paid in full. Any\n" +
                    "grant, scholarship, or loan funds left over after paying direct costs will be giv",
            modifier = Modifier.padding(bottom = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            )

        Text(
            text = "INDIRECT COSTS",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Additional college expenses estimated by Xavier University of Louisiana, but not billed to you, are indirect costs. Indirect\n" +
                    "costs include books, supplies, personal expenses, and transportation expenses. Your actual costs for these items may be\n" +
                    "less than the estimates. We estimate these costs so you have a more accurate understanding of the total cost of\n" +
                    "attending college. ",
            modifier = Modifier.padding(bottom = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            )
    }
}

@Preview
@Composable
fun CostOverviewPreview(){
    CostOverview()
}

@Composable
fun LivingSituation(selectedOption: String,
                    onOptionSelected: (String) -> Unit){
    val radioOptions = listOf("Living on campus (St.Mike's & KD)", "Living on campus (DP & LLC)",
        "Living off campus (UNO)", "Living off campus (LSU)", "Living off campus (not with parents)",
        "Living off campus (with parents)")

    Column(Modifier.selectableGroup()) {
        Text(
            text = "Living Situation",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun StudentLevel
(selectedOption: String,
onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = "Level",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge

    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopStart)
        .clickable { expanded = true }
        .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 20.dp)
    ) {
        Text(
            text = selectedOption,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DropdownMenuItem(
                text = { Text("Undergraduate") },
                onClick = {
                    onOptionSelected("Undergraduate")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Graduate") },
                onClick = {
                    onOptionSelected("Graduate")
                    expanded = false
                }
            )
        }
    }
}
@Composable
fun GraduateProgram(selectedOption: String,
                 onOptionSelected: (String) -> Unit) {
    val programs = listOf("Graduate School (6 hours)", "Doctorate of Education (ED.D.)", "Masters of Public Health",
        "Speech Pathology", "Physician Assistant Program Year 1", "Physician Assistant Program Year 2",
        "Physician Assistant Program Year 3")
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = "Program",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge

    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopStart)
        .clickable { expanded = true }
        .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 20.dp)
    ) {
        Text(
            text = selectedOption,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(16.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            programs.forEach { text ->
                DropdownMenuItem(
                    text = { Text(text) },
                    onClick = {
                        onOptionSelected(text)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CostOfAttendance(){
    val (semester, setSemester) = remember { mutableStateOf("Both Fall and Spring 2024-2025") }
    val (livingSituation, setLivingSituation) = remember { mutableStateOf("Living on campus (St.Mike's & KD)")}
    val (studentLevel, setStudentLevel) = remember { mutableStateOf("Please select") }
    val (graduateProgram, setGraduateProgram) = remember { mutableStateOf("Please select") }

    //Estimated Cost
    var totalCost by remember {mutableStateOf("__,___")}

    // Cost Breakdown
    var tuition by remember {mutableStateOf("__,___")}
    var fees by remember {mutableStateOf("__,___")}
    var livingAllowance by remember {mutableStateOf("__,___")}
    var booksAndSupplies by remember {mutableStateOf("__,___")}
    var transportation by remember {mutableStateOf("__,___")}
    var personalOrMiscellaneous by remember {mutableStateOf("__,___")}

    totalCost = when {
        studentLevel == "Undergraduate" && semester == "Both Fall and Spring 2024-2025" -> {
            when (livingSituation) {
                "Living off campus (with parents)" -> "40,386"
                "Living off campus (not with parents)" -> "52,305"
                "Living on campus (St.Mike's & KD)" -> "46,875"
                "Living on campus (DP & LLC)" -> "47,746"
                "Living off campus (UNO)" -> "46,989"
                "Living off campus (LSU)" -> "50,633"
                else -> "__,___"
            }
        }
        studentLevel == "Graduate" -> {
            when {
                graduateProgram == "Graduate School (6 hours)" -> "27,948"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Doctorate of Education (ED.D.)" -> "40,574"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Masters of Public Health" -> "45,465"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Speech Pathology" -> "48,345"
                (semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 1" -> "59,496"
                (semester == "Fall" || semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 2" -> "85,415"
                graduateProgram == "Physician Assistant Program Year 3" -> "57,525"
                else -> "__,___"
            }
        }
        else -> "__,___"
    }

    tuition = when {
        studentLevel == "Undergraduate" && semester == "Both Fall and Spring 2024-2025" -> "25,829"

        studentLevel == "Graduate" -> {
            when {
                graduateProgram == "Graduate School (6 hours)" -> "3,120"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Doctorate of Education (ED.D.)" -> "15,276"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Masters of Public Health" -> "18,529"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Speech Pathology" -> "22,957"
                (semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 1" -> "27,142"
                (semester == "Fall" || semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 2" -> "40,713"
                graduateProgram == "Physician Assistant Program Year 3" -> "27,142"
                else -> "__,___"
            }
        }
        else -> "__,___"
    }

    fees = when {
        studentLevel == "Undergraduate" && semester == "Both Fall and Spring 2024-2025" -> {
            when (livingSituation) {
                "Living off campus (with parents)" -> "2,841"
                "Living off campus (not with parents)" -> "2,841"
                "Living on campus (St.Mike's & KD)" -> "3,087"
                "Living on campus (DP & LLC)" -> "3,087"
                "Living off campus (UNO)" -> "3,003"
                "Living off campus (LSU)" -> "3,003"
                else -> "__,___"
            }
        }
        studentLevel == "Graduate" -> {
            when {
                graduateProgram == "Graduate School (6 hours)" -> "1,193"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Doctorate of Education (ED.D.)" -> "1,663"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Masters of Public Health" -> "3,301"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Speech Pathology" -> "1,753"
                (semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 1" -> "6,369"
                (semester == "Fall" || semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 2" -> "5,577"
                graduateProgram == "Physician Assistant Program Year 3" -> "4,019"
                else -> "__,___"
            }
        }
        else -> "__,___"
    }

    livingAllowance = when {
        studentLevel == "Undergraduate" && semester == "Both Fall and Spring 2024-2025" -> {
            when (livingSituation) {
                "Living off campus (with parents)" -> "4,196"
                "Living off campus (not with parents)" -> "16,115"
                "Living on campus (St.Mike's & KD)" -> "10,439"
                "Living on campus (DP & LLC)" -> "11,310"
                "Living off campus (UNO)" -> "10,637"
                "Living off campus (LSU)" -> "14,281"
                else -> "__,___"
            }
        }
        studentLevel == "Graduate" -> {
            when {
                graduateProgram == "Graduate School (6 hours)" -> "16,115"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Doctorate of Education (ED.D.)" -> "16,115"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Masters of Public Health" -> "16,115"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Speech Pathology" -> "16,115"
                (semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 1" -> "16,115"
                (semester == "Fall" || semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 2" -> "24,172"
                graduateProgram == "Physician Assistant Program Year 3" -> "16,115"
                else -> "__,___"
            }
        }
        else -> "__,___"
    }

    booksAndSupplies = when {
        studentLevel == "Undergraduate" && semester == "Both Fall and Spring 2024-2025" -> "1,353"
        studentLevel == "Graduate" -> {
            when {
                graduateProgram == "Graduate School (6 hours)" -> "1,353"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Doctorate of Education (ED.D.)" -> "1,353"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Masters of Public Health" -> "1,353"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Speech Pathology" -> "1,353"
                (semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 1" -> "3,703"
                (semester == "Fall" || semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 2" -> "5,640"
                graduateProgram == "Physician Assistant Program Year 3" -> "4,082"
                else -> "__,___"
            }
        }
        else -> "__,___"
    }

    transportation = when {
        studentLevel == "Undergraduate" && semester == "Both Fall and Spring 2024-2025" -> "3,564"
        studentLevel == "Graduate" -> {
            when {
                graduateProgram == "Graduate School (6 hours)" -> "3,564"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Doctorate of Education (ED.D.)" -> "3,564"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Masters of Public Health" -> "3,564"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Speech Pathology" -> "3,564"
                (semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 1" -> "3,564"
                (semester == "Fall" || semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 2" -> "5,346"
                graduateProgram == "Physician Assistant Program Year 3" -> "3,564"
                else -> "__,___"
            }
        }
        else -> "__,___"
    }

    personalOrMiscellaneous = when {
        studentLevel == "Undergraduate" && semester == "Both Fall and Spring 2024-2025" -> "2,603"
        studentLevel == "Graduate" -> {
            when {
                graduateProgram == "Graduate School (6 hours)" -> "2,603"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Doctorate of Education (ED.D.)" -> "2,603"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Masters of Public Health" -> "2,603"
                semester == "Both Fall and Spring 2024-2025" && graduateProgram == "Speech Pathology" -> "2,603"
                (semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 1" -> "2,603"
                (semester == "Fall" || semester == "Spring" || semester == "Summer") && graduateProgram == "Physician Assistant Program Year 2" -> "3,904"
                graduateProgram == "Physician Assistant Program Year 3" -> "2,603"
                else -> "__,___"
            }
        }
        else -> "__,___"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(16.dp)
    ) {
        Text(
            text = "Calculate Tuition + Cost of Attendance",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            )

        SemestersAsRadioButtons(selectedOption = semester,
            onOptionSelected = { setSemester(it) })
        HorizontalDivider()

        StudentLevel(selectedOption = studentLevel,
            onOptionSelected = { setStudentLevel(it) })
        HorizontalDivider()

        if(studentLevel == "Undergraduate") {
            LivingSituation(selectedOption = livingSituation,
                onOptionSelected = { setLivingSituation(it) })
            HorizontalDivider()
        } else if(studentLevel == "Graduate"){
            GraduateProgram(selectedOption = graduateProgram,
                onOptionSelected = { setGraduateProgram(it) })
            HorizontalDivider()
        }

        Text(
            text = "ESTIMATED COSTS OF ATTENDANCE FOR 2024-25",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Text(
            text = "$$totalCost",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Cost Breakdown",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Blue
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Tuition",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .padding(end = 6.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$$tuition",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Fees",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .padding(end = 6.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$$fees",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Living Allowance",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .padding(end = 6.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$$livingAllowance",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Books & Supplies",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .padding(end = 6.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$$booksAndSupplies",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Transportation",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .padding(end = 6.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$$transportation",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Personal/Miscellaneous",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .padding(end = 6.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$$personalOrMiscellaneous",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
fun CostOfAttendancePreview(){
    CostOfAttendance()
}