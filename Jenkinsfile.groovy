#!groovy

task_branch = "${TEST_BRANCH_NAME}"
def branch_cutted = task_branch.contains("origin") & task_branch.split('/')[1]; task_branch.trim()
currentBuild.displayName = "$branch_cutted"
base_git_url = "https://github.com/Alenin556/MavenAllureJenkins.git"

node {
    withEnv(["branch = ${branch_cutted}", "base_url=${base_git_url}"]) {
        stage("Checkout Branch") {
            if (!"$branch_cutted".contains("master")) {
                try {
                    getProject("$base_git_url", "$branch_cutted")
                } catch (err) {
                    echo "Failed get Branch $branch_cutted"
                    throw ("$err")
                }
            } else {
                echo "Current branch master"
            }
        }

        try{
            stage("Run tests") {
                parallel(
                        'Smoke' : {
                            runTestWithTag("smoke")
                        },
                        'Regres' : {
                            runTestWithTag("regres")
                        }
                )
            }
        } finally {
            stage ("Report") {
                getAllureReport()
                    }
        }
    }


}

def runTestWithTag(String tag) {
    try {
        labelledShell(label : "Run ${tag}", script: "mvn test -D groups=${tag}")
    } finally {
        echo "test run finish"
    }
}


def getProject(String repo, String branch) {
    cleanWs()
    checkout scm: [
            $class           : 'GitSCM', branches: [[name: branch]],
            userRemoteConfigs: [[
                                        url: repo
                                ]]
    ]
}

def getAllureReport(String repo, String branch) {
    allure([
            includePropeties: true,
            jdk: '',
            properties: [],
            reportBuildPolicy: 'ALWAYS',
            results: [[path: "target/allure-results"]],
    ])

}
