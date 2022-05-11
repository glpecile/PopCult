import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import {styled} from "@mui/material/styles";
import StepConnector, {stepConnectorClasses} from "@mui/material/StepConnector";
import Check from "@mui/icons-material/Check";
import {useTranslation} from "react-i18next";
import Spinner from "../../animation/Spinner";
import {useContext, useEffect, useState} from "react";
import FirstStep from "./FirstStep";
import SecondStep from "./SecondStep";
import ThirdStep from "./ThirdStep";
import LastStep from "./LastStep";
import ListService from "../../../services/ListService";
import collaborativeService from "../../../services/CollaborativeService";
import {useNavigate} from "react-router-dom";
import AuthContext from "../../../store/AuthContext";


const violetConnector = styled(StepConnector)(({theme}) => ({
    [`&.${stepConnectorClasses.alternativeLabel}`]: {
        top: 10,
        left: 'calc(-50% + 16px)',
        right: 'calc(50% + 16px)',
    },
    [`&.${stepConnectorClasses.active}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            borderColor: 'rgb(139 92 246)',
        },
    },
    [`&.${stepConnectorClasses.completed}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            borderColor: 'rgb(139 92 246)', //bg-violet-500
        },
    },
    [`& .${stepConnectorClasses.line}`]: {
        borderColor: theme.palette.mode === 'dark' ? theme.palette.grey[800] : '#eaeaf0',
        borderTopWidth: 3,
        borderRadius: 1,
    },
}));

const violetStepIconRoot = styled('div')(({theme, ownerState}) => ({
    color: theme.palette.mode === 'dark' ? theme.palette.grey[700] : '#eaeaf0',
    display: 'flex',
    height: 22,
    alignItems: 'center',
    ...(ownerState.active && {
        color: 'rgb(139 92 246)',
    }),
    '& .QontoStepIcon-completedIcon': {
        color: 'rgb(139 92 246)',
        zIndex: 1,
        fontSize: 18,
    },
    '& .QontoStepIcon-circle': {
        width: 8,
        height: 8,
        borderRadius: '50%',
        backgroundColor: 'currentColor',
    },
}));

function violetStepIcon(props) {
    const {active, completed, className} = props;

    return (
        <violetStepIconRoot ownerState={{active}} className={className}>
            {completed ? (
                <Check className="QontoStepIcon-completedIcon"/>
            ) : (
                <div className="QontoStepIcon-circle"/>
            )}
        </violetStepIconRoot>
    );
}

export default function NewListStepper() {
    const {t} = useTranslation();

    const username = useContext(AuthContext).username;

    const steps = [t('lists_step1'), t('lists_step2'), t('lists_step3'), t('lists_step_finish')];
    const [activeStep, setActiveStep] = useState(0);
    const [isValidStep, setValidStep] = useState(false);

    const [listName, setListName] = useState('');
    const [listDescription, setListDescription] = useState('');

    const [isCollaborative, setIsCollaborative] = useState(false);
    const [isPublic, setIsPublic] = useState(false);

    const [addedCollaborators, setAddedCollaborators] = useState(() => new Map());
    const [addedMedia, setAddedMedia] = useState(() => new Map());

    const [toSubmit, setToSubmit] = useState(false);

    const navigate = useNavigate();

    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleFinish = () => {
        setToSubmit(true);
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    }

    useEffect(() => {
        if (toSubmit) {
            async function createNewList() {
                try {
                    const listUrl = await ListService.createList(listName, listDescription, isPublic, isCollaborative);
                    const data = await ListService.getList(listUrl);
                    await ListService.manageMediaInList({url: data.mediaUrl, add: Array.from(addedMedia.keys()), remove: []});
                    await collaborativeService.manageListCollaborators({url: data.collaboratorsUrl, add: Array.from(addedCollaborators.keys()), remove: []});
                    navigate('/lists/' + data.id);
                } catch (error) {
                    console.log(error);
                }
            }

            createNewList();
        }
    }, [toSubmit, listName, listDescription, isPublic, isCollaborative, addedCollaborators, addedMedia, navigate])

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    return (
        <Box sx={{width: '100%'}}>
            <Stepper activeStep={activeStep} alternativeLabel connector={<violetConnector/>}>
                {steps.map((label, index) => {
                    const stepProps = {};
                    const labelProps = {};
                    return (
                        <Step key={label} {...stepProps}>
                            <StepLabel StepIconComponent={violetStepIcon} {...labelProps}>{label}</StepLabel>
                        </Step>
                    );
                })}
            </Stepper>
            {activeStep === steps.length ? (
                <div className="flex flex-col align-items-center py-3">
                    <Spinner/>
                    {t('lists_step_end')}
                </div>
            ) : (
                <>
                    {/*STEP 0*/}
                    {activeStep === 0 &&
                        <FirstStep listName={listName} listDescription={listDescription} setListName={setListName}
                                   setListDescription={setListDescription} setState={setValidStep}/>}
                    {/*STEP 1*/}
                    {activeStep === 1 && <SecondStep addedMedia={addedMedia} setAddedMedia={setAddedMedia}/>}
                    {/*STEP 2*/}
                    {activeStep === 2 &&
                        <ThirdStep isPublic={isPublic} setPublic={setIsPublic} isCollaborative={isCollaborative}
                                   setCollaborative={setIsCollaborative} addedCollaborators={addedCollaborators}
                                   setAddedCollaborators={setAddedCollaborators} ownerUsername={username}/>}
                    {/*STEP 3*/}
                    {activeStep === 3 && <LastStep name={listName} description={listDescription} isPublic={isPublic}
                                                   isCollaborative={isCollaborative}
                                                   collaborators={Array.from(addedCollaborators.values())}
                                                   media={Array.from(addedMedia.values())}/>}

                    {/*BOTTOM BUTTONS*/}
                    <div className="flex justify-between">
                        <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded"
                                disabled={activeStep === 0}
                                onClick={handleBack}
                        >
                            {t('lists_step_back')}
                        </button>
                        <button
                            className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded"
                            disabled={isValidStep === false}
                            onClick={activeStep === steps.length - 1 ? handleFinish : handleNext}>
                            {activeStep === steps.length - 1 ? (t('lists_step_finish')) : (t('lists_step_next'))}
                        </button>
                    </div>
                </>
            )}
        </Box>
    );
}