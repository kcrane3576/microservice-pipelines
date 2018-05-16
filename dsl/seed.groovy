def createPipelineJob(jobName, repoUrl) {
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches('master')
                        extensions {
                            cleanBeforeCheckout()
                        }
                    }
                }
                scriptPath("Jenkinsfile")
            }
        }
    }
}

def createMultibranchPipelineJob(jobName, repoUrl) {
    multibranchPipelineJob(jobName) {
        branchSources {
            git {
                remote(repoUrl)
                includes('*')
            }
        }
        triggers {
            cron("H/5 * * * *")
        }
    }
}

def buildPipelineJobs() {
    def repo = "https://github.com/kcrane3576/"
    def repoUrl = repo + jobName + ".git"
    def deployName = jobName + "_deploy"
    def testName = jobName + "_test"

    createPipelineJob(deployName, repoUrl)
    createMultibranchPipelineJob(testName, repoUrl)
}

def pipelineConfig = getPipelineConfig()
buildPipelineJobs()