import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import {styled} from "@mui/material/styles";
import StepConnector, {stepConnectorClasses} from "@mui/material/StepConnector";
import Check from "@mui/icons-material/Check";
import {useTranslation} from "react-i18next";
import Spinner from "../animation/Spinner";
import {useState} from "react";
import {Switch} from "@mui/material";


const PurpleConnector = styled(StepConnector)(({theme}) => ({
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

const PurpleStepIconRoot = styled('div')(({theme, ownerState}) => ({
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

function PurpleStepIcon(props) {
    const {active, completed, className} = props;

    return (
        <PurpleStepIconRoot ownerState={{active}} className={className}>
            {completed ? (
                <Check className="QontoStepIcon-completedIcon"/>
            ) : (
                <div className="QontoStepIcon-circle"/>
            )}
        </PurpleStepIconRoot>
    );
}

export default function NewListStepper(props) {
    const {t} = useTranslation();
    const steps = [t('lists_step1'), t('lists_step2'), t('lists_step3'), t('lists_step_finish')];
    const [activeStep, setActiveStep] = useState(0);
    const [isValidStep, setValidStep] = useState(false);

    const [listName, setListName] = useState('');
    const [listNameError, setListNameError] = useState(false);
    const [listDescription, setListDescription] = useState('');
    const [listDescriptionError, setListDescriptionError] = useState(false);

    const [isCollaborative, setIsCollaborative] = useState(false);
    const [isPublic, setIsPublic] = useState(false);

    const listNameHandler = (event) => {
        event.target.validity.valid ? (event.target.value.length === 0 ? setListNameError(true) : setListNameError(false)) : setListNameError(true);
        setListName(event.target.value);
        if (event.target.validity.valid === false|| event.target.value.length === 0 || listDescriptionError === true) setValidStep(false)
        else setValidStep(true);

    }
    const listDescriptionHandler = (event) => {
        //TODO check this regex behaves weird
        let valid = /[^/><]+/.test(event.target.value) || event.target.value.length === 0;
        valid ? setListDescriptionError(false) : setListDescriptionError(true);
        setListDescription(event.target.value);
        if (valid === false || listNameError === true || listName.length === 0) setValidStep(false);
        else setValidStep(true);
    }

    const setCollaborative = () => {
        setIsCollaborative(!isCollaborative);
    }

    const setPublic = () => {
        setIsPublic(!isPublic);
    }

    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    return (
        <Box sx={{width: '100%'}}>
            <Stepper activeStep={activeStep} alternativeLabel connector={<PurpleConnector/>}>
                {steps.map((label, index) => {
                    const stepProps = {};
                    const labelProps = {};
                    return (
                        <Step key={label} {...stepProps}>
                            <StepLabel StepIconComponent={PurpleStepIcon} {...labelProps}>{label}</StepLabel>
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
                    {activeStep === 0 && <>
                        <div className="px-5 pt-3 text-semibold w-full">
                            {/*list name input*/}
                            <label
                                className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-violet-400">
                                {t('lists_listName')}
                            </label>
                            <input
                                className={"rounded w-full bg-gray-50" + (listNameError ? "border-2 border-rose-500" : "")}
                                type='text' value={listName}
                                onChange={listNameHandler} pattern="[^/><]+" minLength={1} maxLength={100}/>

                            {/*description textarea*/}
                            <label className="py-2 text-semibold w-full after:ml-0.5 after:text-violet-400">
                                {t('lists_description')}
                            </label>
                            <textarea
                                className={"rounded w-full bg-gray-50" + (listDescriptionError ? "border-2 border-rose-500" : "")}
                                value={listDescription} onChange={listDescriptionHandler}/>
                        </div>
                    </>}
                    {/*STEP 1*/}
                    {activeStep === 1 && <div className="flex justify-center"><Spinner/></div>}
                    {/*STEP 2*/}
                    {activeStep === 2 &&
                        <div className="px-5 pt-3 my-3 space-y-2 text-semibold w-full">
                            <div className="flex justify-between">
                                {t('lists_public')}
                                <div className="justify-end">
                                    <Switch onClick={setPublic} color="secondary"/>
                                </div>
                            </div>
                            <div className="flex justify-between">
                                {t('lists_collaborative')}
                                <div className="justify-end">
                                    <Switch onClick={setCollaborative} color="secondary"/>
                                </div>
                            </div>
                            {isCollaborative &&
                                <div>
                                    Add collaborators.
                                </div>}
                        </div>}
                    {/*STEP 3*/}
                    {activeStep === 3 && <div className="flex justify-center"><Spinner/></div>}
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
                            onClick={handleNext}>
                            {activeStep === steps.length - 1 ? (t('lists_step_finish')) : (t('lists_step_next'))}
                        </button>
                    </div>
                </>
            )}
        </Box>
    );
}